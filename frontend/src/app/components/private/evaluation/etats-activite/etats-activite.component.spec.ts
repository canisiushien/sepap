import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtatsActiviteComponent } from './etats-activite.component';

describe('EtatsActiviteComponent', () => {
  let component: EtatsActiviteComponent;
  let fixture: ComponentFixture<EtatsActiviteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EtatsActiviteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtatsActiviteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
