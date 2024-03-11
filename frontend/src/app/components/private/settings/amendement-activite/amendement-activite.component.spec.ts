import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AmendementActiviteComponent } from './amendement-activite.component';

describe('AmendementActiviteComponent', () => {
  let component: AmendementActiviteComponent;
  let fixture: ComponentFixture<AmendementActiviteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AmendementActiviteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AmendementActiviteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
