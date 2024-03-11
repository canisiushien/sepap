import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CritereGouvernanceComponent } from './critere-gouvernance.component';

describe('CritereGouvernanceComponent', () => {
  let component: CritereGouvernanceComponent;
  let fixture: ComponentFixture<CritereGouvernanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CritereGouvernanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CritereGouvernanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
