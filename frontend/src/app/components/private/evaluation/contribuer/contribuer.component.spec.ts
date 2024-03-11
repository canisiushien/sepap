import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContribuerComponent } from './contribuer.component';

describe('ContribuerComponent', () => {
  let component: ContribuerComponent;
  let fixture: ComponentFixture<ContribuerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContribuerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContribuerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
